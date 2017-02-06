package edu.knoldus

import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._

class TweetFeedsTest extends FunSuite {
  val obj = new TweetFeeds()
  val result = Await.result(obj.fetchDataFromTwitter(), 30.second)
  val like = Await.result(obj.getAvgLike(result), 30.second)
  val rtwts = Await.result((obj.getAvgretweet(result)), 30.second)

  test("Is there any Tweets exsit") {
    assert(result.size >= 0)
  }
  test("Is Fetched tweets are correct") {
    assert(result.size == 100)
  }

  test("Is there any like") {
    val like = Await.result(obj.getNoLike(result), 30.second)
    assert(like > 0)
  }
  test("Is avg like is correct") {
    assert(like >= 0.0)
  }
  test("Is there any retweets") {
    val rtweet = Await.result(obj.getNoRetweet(result), 30.second)
    assert(rtweet > 0)
  }
  test("Is avg retweets is correct") {
    assert(rtwts >= 0.0)
  }
}
