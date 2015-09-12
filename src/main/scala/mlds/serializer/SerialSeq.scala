package mlds.serializer

import scala.util.Random

/**
 * Created by vyacheslav.baranov on 11/09/15.
 */
trait SerialSeq extends Serializable {

  def id: Int
  def items: Iterable[SerialBean]

}

case class SerialSeq1
(
  id: Int,
  items: Iterable[SerialBean]
) extends SerialSeq

object SerialSeq1 {

  def apply(rnd: Random): SerialSeq = {
    val items = (0 until 1000).map(_ => SerialBean(rnd))
    SerialSeq1(rnd.nextInt(10000), items)
  }

  def createPartition(idx: Int): Iterator[SerialSeq] = {
    val rnd = new Random(12345L + idx)
    (0 until 8000).iterator.map(_ => SerialSeq1(rnd))
  }
}

case class SerialSeq2
(
  id: Int,
  itemKeys: Array[Int],
  itemValues: Array[Long]
) extends SerialSeq {

  require(itemKeys.length == itemValues.length)

  override def items = (0 until itemKeys.length).map { i =>
    SerialBean(itemKeys(i), itemValues(i))
  }
}

object SerialSeq2 {
  def apply(rnd: Random): SerialSeq = {
    val items = (0 until 1000).map(_ => SerialBean(rnd))
    SerialSeq2(rnd.nextInt(10000), items.map(_.key).toArray, items.map(_.value).toArray)
  }

  def createPartition(idx: Int): Iterator[SerialSeq] = {
    val rnd = new Random(12345L + idx)
    (0 until 8000).iterator.map(_ => SerialSeq2(rnd))
  }

}
