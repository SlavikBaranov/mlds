package mlds.serializer

import scala.util.Random

/**
 * Created by vyacheslav.baranov on 11/09/15.
 */
case class SerialBean
(
  key: Int,
  value: Long
) extends Serializable

object SerialBean {

  def apply(rnd: Random): SerialBean = {
    SerialBean(
      rnd.nextInt(10000),
      rnd.nextLong()
    )
  }

}
