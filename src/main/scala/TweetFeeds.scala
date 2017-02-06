package edu.knoldus

import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Query, Twitter, TwitterFactory}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TweetFeeds {

  /**
    * method for fetching the twees on the basis of hashtag
    *
    */
  def fetchDataFromTwitter(): Future[List[MyTweets]] = Future {

    val consumerKey = "consumerKey"
    val consumerSecretKey = "consumerSecretKey"
    val accessToken = "accessToken"
    val accessTokenSecret = "accessTokenSecret"

    val configurationBuilder = new ConfigurationBuilder()
    configurationBuilder.setDebugEnabled(false)
      .setOAuthConsumerKey(consumerKey)
      .setOAuthConsumerSecret(consumerSecretKey)
      .setOAuthAccessToken(accessToken)
      .setOAuthAccessTokenSecret(accessTokenSecret)

    val twitter: Twitter = new TwitterFactory(configurationBuilder.build()).getInstance()

    val query = new Query("#bigdata")
    val count = 100
    query.setCount(count)
    val list = twitter.search(query)
    val tweets = list.getTweets.asScala.toList
    val allTweets = tweets.map {
      tweet =>
        MyTweets(tweet.getText, tweet.getUser.getScreenName, tweet.getFavoriteCount, tweet.getRetweetCount)

    }
    allTweets

  }

  /**
    * method for calculating no of likes
    * @param original
    * @return
    */

  def getNoLike(original: List[MyTweets]): Future[Int] = Future {
    val liklist = original map (_.FavoriteCount)
    val sum = liklist.foldLeft(0)(_ + _)
    sum
  }

  /**
    * method for calculating avg like
    * @param original
    * @return
    */

  def getAvgLike(original: List[MyTweets]): Future[Float] = Future {
    val likelist = original map (_.FavoriteCount)
    val sum = likelist.foldLeft(0)(_ + _)
    val avg = sum / 100
    avg

  }

  /**
    * method for calculating no of retweets
    * @param original
    * @return
    */
  def getNoRetweet(original: List[MyTweets]): Future[Int] = Future {
    val relist = original map (_.RetweetCount)
    val sum = relist.foldLeft(0)(_ + _)
    sum
  }

  /**
    * method for calculating avg of retweets
    * @param original
    * @return
    */
  def getAvgretweet(original: List[MyTweets]): Future[Float] = Future {
    val retweetlist = original map (_.RetweetCount)
    val sum = retweetlist.foldLeft(0)(_ + _)
    val avg = sum / 100
    avg

  }
}
