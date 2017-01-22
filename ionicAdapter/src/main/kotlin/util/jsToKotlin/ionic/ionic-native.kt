package util.jsToKotlin.ionic


@JsModule("ionic-native")
external object ionicNative {

    @JsName("SQLite")
    class SQLite() {
        var _objectInstance: Any = noImpl
        var databaseFeatures: Any = noImpl
        fun openDatabase(config: Any): Promise<SQLite> = noImpl
        fun addTransaction(transaction: Any): Unit = noImpl
        fun transaction(fn: Any): Promise<Any> = noImpl
        fun readTransaction(fn: Any): Promise<Any> = noImpl
        fun startNextTransaction(): Unit = noImpl
        fun close(): Promise<Any> = noImpl
        fun start(): Unit = noImpl
        fun executeSql(statement: String, params: Any): Promise<Any> = noImpl
        fun addStatement(sql: Any, values: Any): Promise<Any> = noImpl
        fun sqlBatch(sqlStatements: Any): Promise<Any> = noImpl
        fun abortallPendingTransactions(): Unit = noImpl
        fun handleStatementSuccess(handler: Any, response: Any): Unit = noImpl
        fun handleStatementFailure(handler: Any, response: Any): Unit = noImpl
        fun run(): Unit = noImpl
        fun abort(txFailure: Any): Unit = noImpl
        fun finish(): Unit = noImpl
        fun abortFromQ(sqlerror: Any): Unit = noImpl

        companion object {
            fun openDatabase(config: Any): Promise<SQLite> = noImpl
            fun echoTest(): Promise<Any> = noImpl
            fun deleteDatabase(first: Any): Promise<Any> = noImpl
        }
    }
}

@JsName("Promise")
external class Promise<out E> {
    fun then(invoke: (db: E) -> Unit): Promise<E> = noImpl
    fun catch(handle: (err: Any) -> Unit): Promise<E> = noImpl

}
