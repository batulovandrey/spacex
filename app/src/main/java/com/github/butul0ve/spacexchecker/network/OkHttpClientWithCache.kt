package com.github.butul0ve.spacexchecker.network

import android.content.Context
import com.github.butul0ve.spacexchecker.utils.isConnectedToInternet
import okhttp3.Cache
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

class OkHttpClientWithCache {

    companion object {

        private var INSTANCE: OkHttpClient? = null

        fun getInstance(context: Context): OkHttpClient {

            if (INSTANCE == null) {

                synchronized(OkHttpClientWithCache::class) {
                    val cacheSize = (10 * 1024 * 1024).toLong()
                    val cache = Cache(context.cacheDir, cacheSize)

                    val sc = SSLContext.getInstance("TLSv1.2")
                    sc.init(null, null, null)

                    val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                            .tlsVersions(TlsVersion.TLS_1_2)
                            .build()

                    val specs = ArrayList<ConnectionSpec>()
                    specs.add(cs)
                    specs.add(ConnectionSpec.COMPATIBLE_TLS)
                    specs.add(ConnectionSpec.CLEARTEXT)

                    INSTANCE = OkHttpClient.Builder()
                            .cache(cache)
                            .sslSocketFactory(NoSSLv3SocketFactory(sc.socketFactory),
                                    object : X509TrustManager {
                                        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

                                        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

                                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                                            return arrayOf()
                                        }
                                    })
                            .connectionSpecs(specs)
                            .addInterceptor { chain ->

                                var request = chain.request()

                                request = if (context.isConnectedToInternet()) {
                                    request.newBuilder().header("Cache-Control", "public, max-age" + 10).build()
                                } else {
                                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                                }
                                chain.proceed(request)
                            }.build()
                }
            }

            return INSTANCE!!
        }
    }
}