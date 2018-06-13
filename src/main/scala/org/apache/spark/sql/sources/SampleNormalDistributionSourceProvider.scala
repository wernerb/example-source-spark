package org.apache.spark.sql.sources

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.execution.streaming.Source
import org.apache.spark.sql.types.StructType


// This is the actual streaming data source which will be created using the earlier create normal source class
class SampleNormalDistributionSourceProvider extends StreamSourceProvider with DataSourceRegister {

  override def sourceSchema(sqlContext: SQLContext,
                            schema: Option[StructType],
                            providerName: String,
                            parameters: Map[String, String]): (String, StructType) = ("randomNumberGenerator", Schemas.sampleSchema)

  override def createSource(sqlContext: SQLContext,
                            metadataPath: String,
                            schema: Option[StructType],
                            providerName: String,
                            parameters: Map[String, String]): Source =
    new SampleNormalDistributionSource(sqlContext)

  override def shortName(): String = "randomNumberGenerator"
}