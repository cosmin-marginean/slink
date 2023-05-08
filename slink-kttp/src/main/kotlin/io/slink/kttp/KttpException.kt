package io.slink.kttp

import okhttp3.Response

class KttpException(
    val url: String,
    val code: Int,
    val responseBody: String
) : RuntimeException("Error on HTTP request ${url}. Status code is $code and response body is $responseBody") {

    constructor(response: Response) : this(response.request.url.toString(), response.code, response.text())
}
