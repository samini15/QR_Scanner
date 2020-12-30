package com.shayan.qrnfcscanner.model

import com.google.firebase.Timestamp

/*sealed class Code {
    abstract var dateCreated: Date
}

@Parcelize
data class QRCode (override var dateCreated: Date = Date(), val url: String) : Code(), Parcelable

@Parcelize
data class Nfc (override var dateCreated: Date = Date(), val url: String) : Code(), Parcelable*/

//@Parcelize
data class Code(var id: String, var dateCreated: Timestamp, var url: String)/* : Parcelable*/ {
    constructor() : this("", Timestamp.now(),"")
}