package org.thompson

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.types.StructType
import org.scalacheck.Gen

object StructTypeUtils {
  implicit class dataGenerator(structType: StructType) {
      def dataGenerator(rowGen: Gen[Row], numSlice: Int, numRowPerSlice: Int)
                       (implicit sc: SparkContext): DataFrame = {
        val spark: SparkSession = SparkSession
          .builder()
          .config(sc.getConf)
          .getOrCreate()

        lazy val rowSeq: Seq[Row] = Gen.listOfN(numRowPerSlice, rowGen).sample.get
        val genRDD: RDD[Seq[Row]] = sc.parallelize(List.fill(numSlice)(rowSeq), numSlice)
        val rowRdd: RDD[Row] = genRDD.flatMap(x => x)
        spark.createDataFrame(rowRdd, structType)
      }
  }
}
