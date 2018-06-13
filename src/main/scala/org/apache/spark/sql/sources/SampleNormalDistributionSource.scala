package org.apache.spark.sql.sources

import org.apache.spark.sql.execution.streaming.{LongOffset, Offset, Source}
import org.apache.spark.sql.sources.Schemas.Sample
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.json4s.DefaultFormats


class SampleNormalDistributionSource(sqlContext: SQLContext) extends Source {

  implicit val formats: DefaultFormats.type = DefaultFormats

  import sqlContext.implicits._

  override def schema: StructType = Schemas.sampleSchema

  private def getLatestDF: DataFrame =
    sqlContext
      .sparkSession
      .sparkContext
      .parallelize(1 to 20)
      .map(_ => breeze.stats.distributions.Gaussian(0, 1).sample(1000))
      .flatMap(list => list)
      .map(Sample)
      .toDF()

  /** Returns the maximum available offset for this source. */
  override def getOffset: Option[Offset] = {
    Some(LongOffset(System.currentTimeMillis()))
  }

  // Return streaming dataframe based on offsets
  override def getBatch(start: Option[Offset], end: Offset): DataFrame =
    sqlContext.internalCreateDataFrame(getLatestDF.queryExecution.toRdd, Schemas.sampleSchema, isStreaming = true)

  override def stop(): Unit = {

  }
}
