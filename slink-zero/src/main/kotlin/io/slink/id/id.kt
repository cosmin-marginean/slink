package io.slink.id

import java.util.*

/**
 * Copy-paste friendly, lowercase UUID
 */
fun uuid(): String {
    return UUID.randomUUID()
            .toString()
            .lowercase(Locale.getDefault())
            .replace("-", "")
}
