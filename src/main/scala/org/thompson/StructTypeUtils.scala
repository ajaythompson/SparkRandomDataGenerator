package org.thompson

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.scalacheck.Gen

object StructTypeUtils {
  implicit class dataGenerator(structType: StructType) {
    /**
     * Fetch a random dataframe for the give row generator
     *
     * @param rowGen         Generator that yields a row
     * @param numSlice       number of slice  or splits
     * @param numRowPerSlice number of records per slice
     * @param spark          spark session
     * @return random row dataframe
     */
    def dataGenerator(rowGen: Gen[Row], numSlice: Int, numRowPerSlice: Int)
                     (implicit spark: SparkSession): DataFrame = {
      val sc = spark.sparkContext
      lazy val rowSeq: Gen[List[Row]] = Gen.listOfN(numRowPerSlice, rowGen)
      val genRDD: RDD[Seq[Row]] = sc.parallelize(List.fill(numSlice)(rowSeq.sample.get), numSlice)
      val rowRdd: RDD[Row] = genRDD.flatMap(x => x)
      spark.createDataFrame(rowRdd, structType)
    }
  }
}
