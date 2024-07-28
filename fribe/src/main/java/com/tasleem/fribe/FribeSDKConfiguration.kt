package com.tasleem.fribe



object FribeSDKConfiguration {
    var clientId: String? = null
        private set
    var secretKey: String? = null
        private set
    var publishableKey: String? = null
        private set

    fun setup(clientId: String, secretKey: String, publishableKey: String) {
        println("Initialized Fribe Keys....")
        this.clientId = clientId
        this.secretKey = secretKey
        this.publishableKey = publishableKey
        // Perform any other setup you need
    }
}