package nig.cock.nigger.message.handler

class MessageExceptionHandler {

    fun onException(e: Exception) {
        println("Failed to send message")
        e.printStackTrace()
    }

}
