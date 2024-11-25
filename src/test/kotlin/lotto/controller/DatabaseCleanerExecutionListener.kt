package lotto.controller

import jakarta.persistence.EntityManager
import org.springframework.context.ApplicationContext
import org.springframework.test.context.TestContext
import org.springframework.test.context.support.AbstractTestExecutionListener
import org.springframework.transaction.support.TransactionTemplate

class DatabaseCleanerExecutionListener : AbstractTestExecutionListener() {

    override fun beforeTestMethod(testContext: TestContext) {
        cleanupDatabase(testContext.applicationContext)
    }

    private fun cleanupDatabase(context: ApplicationContext) {
        val entityManager = context.getBean(EntityManager::class.java)
        val transactionTemplate = context.getBean(TransactionTemplate::class.java)

        transactionTemplate.execute {
            entityManager.clear()
            disableReferentialIntegrity(entityManager)
            truncateAllTables(entityManager)
            enableReferentialIntegrity(entityManager)
        }
    }

    private fun disableReferentialIntegrity(entityManager: EntityManager) {
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
    }

    private fun enableReferentialIntegrity(entityManager: EntityManager) {
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
    }

    private fun truncateAllTables(entityManager: EntityManager) {
        findTableNames(entityManager).forEach { tableName ->
            entityManager.createNativeQuery("TRUNCATE TABLE $tableName RESTART IDENTITY").executeUpdate()
        }
    }

    private fun findTableNames(entityManager: EntityManager): List<String> {
        val query = """
            SELECT TABLE_NAME
            FROM INFORMATION_SCHEMA.TABLES
            WHERE TABLE_SCHEMA = 'PUBLIC'
        """.trimIndent()
        return entityManager.createNativeQuery(query).resultList as List<String>
    }
}
