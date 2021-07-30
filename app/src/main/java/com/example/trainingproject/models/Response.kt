package com.example.trainingproject.models

abstract class Response{
    abstract var clientMessage: String
    abstract var countRecord: String
    abstract var statusCode: String
    abstract var status: Int
}

data class PointResponse(
    override var clientMessage: String,
    override var countRecord: String,
    override var statusCode: String,
    override var status: Int,
    var result: List<Point>
) : Response()

data class Point(
    val totalPoint : Long,
    val currentPoint : Long,
    val levelUser : Int
)

data class VideoResponse(
    override var clientMessage: String,
    override var countRecord: String,
    override var statusCode: String,
    override var status: Int,
    val result: VideoResult
) : Response()

data class VideoResult(
    val content : ArrayList<Videos>
)

data class Videos(
    val key : String,
    val value : String
)

data class GiftCardResponse(
    override var clientMessage: String,
    override var countRecord: String,
    override var statusCode: String,
    override var status: Int,
    val result: TransactionDisplay
) : Response()

data class TransactionDisplay(
    val transDisplay: ArrayList<TransactionDetail>
)

data class TransactionDetail(
    val idTransaction: Int,
    val cert_value: String,
    val imgCard: String,
    val imgThumbnail : String,
    val currency : String,
    val merchantName: String,
    val cardNameReceiver: String,
    val cardNameSender: String,
    val cardNum: String,
)

data class CountryResponse(
    override var clientMessage: String,
    override var countRecord: String,
    override var statusCode: String,
    override var status: Int,
    val result: ArrayList<CountryResult>
) : Response()

data class CountryResult(
    val name: String,
    val alpha2Code: String,
    val callingCodes: String,
    val flag: String,
    val regex: String,
    var favorite: Boolean,
)

data class CouponResponse(
    override var clientMessage: String,
    override var countRecord: String,
    override var statusCode: String,
    override var status: Int,
    val result: ArrayList<Coupon>
) : Response()

data class Coupon(
    val merchantName : String,
    val expireDate : String,
    val imageShow : String,
    val percentOff : Int
)
