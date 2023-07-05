package com.mitteloupe.whoami.test.server

import android.util.Log
import java.net.InetAddress
import java.net.Socket
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

class LoggingSSLSocketFactory(
    private val delegate: SSLSocketFactory
) : SSLSocketFactory() {
    override fun getDefaultCipherSuites(): Array<String> = delegate.defaultCipherSuites

    override fun getSupportedCipherSuites(): Array<String> = delegate.supportedCipherSuites

    override fun createSocket(s: Socket?, host: String?, port: Int, autoClose: Boolean): Socket =
        delegate.createSocket(s, host, port, autoClose).apply { enableLogging(this) }

    override fun createSocket(host: String?, port: Int): Socket =
        delegate.createSocket(host, port).apply { enableLogging(this) }

    override fun createSocket(
        host: String?,
        port: Int,
        localHost: InetAddress?,
        localPort: Int
    ): Socket =
        delegate.createSocket(host, port, localHost, localPort).apply { enableLogging(this) }

    override fun createSocket(host: InetAddress?, port: Int): Socket =
        delegate.createSocket(host, port).apply { enableLogging(this) }

    override fun createSocket(
        address: InetAddress?,
        port: Int,
        localAddress: InetAddress?,
        localPort: Int
    ): Socket =
        delegate.createSocket(address, port, localAddress, localPort).apply { enableLogging(this) }

    private fun enableLogging(socket: Socket) {
        if (socket is SSLSocket) {
            socket.addHandshakeCompletedListener { event ->
                Log.d(
                    "SSL",
                    "Handshake completed with peerHost=${event.socket.inetAddress.hostName}"
                )
            }
        }
    }
}
