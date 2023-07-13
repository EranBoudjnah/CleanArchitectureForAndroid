package com.mitteloupe.whoami.test.server

import java.lang.Thread.MAX_PRIORITY
import java.security.KeyStore
import java.util.logging.Level
import java.util.logging.Logger
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import kotlin.concurrent.thread
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer

/**
 * To generate a new keystore and extract the certificate, follow the steps below:
 * 1. Download BouncyCastle from:
 *
 *    https://repo1.maven.org/maven2/org/bouncycastle/bcprov-jdk15on/1.69/
 *
 * 2. Run the below commands on your terminal:
 *
 *   > keytool -genkey -v -alias localhost -ext SAN=dns:localhost -keypass 123456 -storepass 123456
 *       -keyalg RSA -keysize 2048 -validity 10000 -storetype BKS -keystore teststore_keystore.bks
 *       -provider org.bouncycastle.jce.provider.BouncyCastleProvider
 *       -providerpath ~/Downloads/bcprov-jdk15on-1.69.jar
 *   > keytool -exportcert -alias localhost -keystore teststore_keystore.bks -file teststore.crt
 *       -storetype BKS -storepass 123456
 *       -provider org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath
 *   > openssl x509 -inform der -in teststore.crt -out teststore.pem
 *
 * 3. Place teststore_keystore.bks in the assets folder of the app being tested.
 *
 * 4. Place teststore.pem in the raw folder of the app and add it to xml/network_security_config.xml
 *    of the app being tested.
 */
class MockWebServerProvider {
    private val algorithm by lazy {
        KeyManagerFactory.getDefaultAlgorithm()
    }

    private val server by lazy {
        val mockWebServer = MockWebServer()
        val thread = thread(priority = MAX_PRIORITY) {
            mockWebServer.start()
        }
        thread.join()
        Logger.getLogger(MockWebServer::class.java.name).level = Level.ALL

        val keyStorePassword = "123456".toCharArray()
        val serverKeyStore = KeyStore.getInstance("BKS")
        processAssetStream("teststore_keystore.bks") { keyStoreStream ->
            serverKeyStore.load(keyStoreStream, keyStorePassword)
        }

        val keyManagerFactory = KeyManagerFactory.getInstance(algorithm)
            .apply { init(serverKeyStore, keyStorePassword) }

        val trustManagerFactory = TrustManagerFactory.getInstance(algorithm)
            .apply { init(serverKeyStore) }

        val sslContext = SSLContext.getInstance("TLSv1.2")
        sslContext.init(
            keyManagerFactory.keyManagers,
            trustManagerFactory.trustManagers,
            null
        )
        val socketFactory = sslContext.socketFactory

        mockWebServer.useHttps(socketFactory, false)

        mockWebServer
    }

    val serverUrl: String
        get() {
            var result = ""
            val thread = thread(priority = MAX_PRIORITY) {
                result = server.hostName + ":" + server.port
            }
            thread.join()
            return result
        }

    fun mockWebServer(dispatcher: Dispatcher): MockWebServer {
        server.dispatcher = dispatcher

        return server
    }
}
