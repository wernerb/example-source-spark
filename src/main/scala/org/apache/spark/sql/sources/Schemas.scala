package org.apache.spark.sql.sources

import org.apache.spark.sql.catalyst.ScalaReflection
import org.apache.spark.sql.types._


object Schemas {

  case class Sample(value: Double)

  val sampleSchema: StructType = ScalaReflection.schemaFor[Sample].dataType.asInstanceOf[StructType]
}
