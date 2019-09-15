#SPARK DATA GENERATOR
To generate random data with scala check generator with spark framework.
Based on the Spark Struct Type data will be generated.

##Building Spark data generator
Spark data generator is built using SBT. To build Spark and its example programs, run:

```
sbt assembly
```

##Using Spark Shell
Start spark with the data generator jar.
```
.\spark-shell --jars SparkDataGenerator-assembly-0.1.jar
```
Create a spark struct type
```
import org.apache.spark.sql.catalyst.ScalaReflection
import org.apache.spark.sql.types.StructType
   
case class Employee(id: Int, name: String)
val structType = ScalaReflection.schemaFor[Employee].dataType.asInstanceOf[StructType]
```
Create employee gen object as per the need using Generators provided by scalacheck. _https://github.com/typelevel/scalacheck/blob/master/src/main/scala/org/scalacheck/Gen.scala_
```
import org.scalacheck.Gen._
import org.apache.spark.sql.Row
val genEmployee = for{id <- arbitrary[Int] 
                    name <- asciiPrintableStr} yield Row(id, name)
```
Create a data-frame of test data by invoking the dataGenerator function which takes in a 
`[[Gen[Row]]], 
number of slices and 
number of rows.`
```
import org.thompson.StructTypeUtils._
implicit val sparkContext  = sc
val employeeDF = structType.dataGenerator(genEmployee, 2, 200)
```