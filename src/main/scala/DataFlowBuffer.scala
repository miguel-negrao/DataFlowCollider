
/**
 * Created by IntelliJ IDEA.
 * User: miguelnegrao
 * Date: 11/07/12
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */

import akka.dispatch._
import Future._
import de.sciss.synth._


object DataFlowBuffer {

  def readChannel( server: Server = Server.default, path: String, startFrame: Int = 0, numFrames: Int = -1,
                   channels: Seq[ Int ]):CompletableFuture[Buffer] = {
    val b = Promise[Buffer]();
    Buffer.readChannel(server, path, startFrame, numFrames, channels, action(buf => flow{ b << buf}) )
    b
  }

}