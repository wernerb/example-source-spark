package org.apache.spark.sql.sources

import org.apache.spark.sql.SparkSession
import org.scalactic.TolerantNumerics
import org.scalatest.{BeforeAndAfterAll, FunSuite, Matchers}


class TestSampleNormalDistributionSource extends FunSuite with Matchers with BeforeAndAfterAll {

  val epsilon = 1e-1f

  implicit val doubleEq = TolerantNumerics.tolerantDoubleEquality(epsilon)

  test("Getting data from the custom source should work") {

    val spark = SparkSession
      .builder
      .appName("TestSampleNormalDistributionSource")
      .master("local[*]")
      .config("spark.driver.memory", "6g")
      .config("spark.executor.memory", "6g")
      .config("spark.sql.shuffle.partitions", "20")
      .getOrCreate()


    val df = spark.
      readStream.
      format("org.apache.spark.sql.sources.SampleNormalDistributionSourceProvider").
      load()

    val stream = df.writeStream
      .outputMode("append")
      .format("memory")
      .queryName("samples")
      .start()


    Thread.sleep(10000)
    stream.stop()

    assert(
      spark.sql(
        """
          |SELECT
          | AVG(samples.value)
          |FROM
          | samples
        """.stripMargin).first().getDouble(0) === 0.0
    )

    spark.stop()
  }
}
