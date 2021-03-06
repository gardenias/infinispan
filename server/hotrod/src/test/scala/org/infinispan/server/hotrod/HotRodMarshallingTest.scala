package org.infinispan.server.hotrod

import org.infinispan.commons.util.EnumUtil
import org.testng.annotations.Test
import org.testng.Assert._
import org.infinispan.commands.remote.ClusteredGetCommand
import org.infinispan.server.core.AbstractMarshallingTest
import org.infinispan.commons.api.BasicCacheContainer
import org.infinispan.commons.marshall.WrappedByteArray
import org.infinispan.util.ByteString

/**
 * Tests marshalling of Hot Rod classes.
 *
 * @author Galder Zamarreño
 * @since 4.1
 */
@Test(groups = Array("functional"), testName = "server.hotrod.HotRodMarshallingTest")
class HotRodMarshallingTest extends AbstractMarshallingTest {

   def testMarshallingBigByteArrayKey() {
      val cacheKey = getBigByteArray
      val bytes = marshaller.objectToByteBuffer(cacheKey)
      val readKey = marshaller.objectFromByteBuffer(bytes).asInstanceOf[Array[Byte]]
      assertEquals(readKey, cacheKey)
   }

   def testMarshallingCommandWithBigByteArrayKey() {
      val cacheKey = getBigByteArray
      val command = new ClusteredGetCommand(new WrappedByteArray(cacheKey),
         ByteString.fromString(BasicCacheContainer.DEFAULT_CACHE_NAME), EnumUtil.EMPTY_BIT_SET)
      val bytes = marshaller.objectToByteBuffer(command)
      val readCommand = marshaller.objectFromByteBuffer(bytes).asInstanceOf[ClusteredGetCommand]
      assertEquals(readCommand, command)
   }

}
