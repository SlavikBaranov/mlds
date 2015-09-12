package mlds.prepartition

import org.apache.spark.{TaskContext, Partition, Partitioner}
import org.apache.spark.rdd.RDD

import scala.reflect.ClassTag

/**
 * Created by vyacheslav.baranov on 12/09/15.
 */
class PrepartitionedRDD[T: ClassTag]
(
  prev: RDD[T],
  part: Partitioner
) extends RDD[T](prev) {

  override val partitioner = Some(part)

  override def getPartitions: Array[Partition] = firstParent[T].partitions

  override def compute(split: Partition, context: TaskContext): Iterator[T] =
    prev.iterator(split, context)

  //Unfortunately, `getPreferredLocations` method is not accessible with default Spark build.
  //It's necessary to to make it public for this to work.

  //override protected def getPreferredLocations(split: Partition): Seq[String] =
  //  prev.getPreferredLocations(split)

}
