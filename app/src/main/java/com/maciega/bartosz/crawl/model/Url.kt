package com.maciega.bartosz.crawl.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Bartosz on 2016-10-18.
 */

open class Url : RealmObject() {

    @PrimaryKey
    var url: String? = null
}
