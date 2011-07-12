/**
 * Created by IntelliJ IDEA.
 * User: miguelnegrao
 * Date: 11/07/12
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 */

import akka.dispatch._
import Future._
import de.sciss.synth._

object DataFlowSynthDef {

   def load( name: String, s:Server )( thunk: => Unit ):CompletableFuture[SynthDef] = {
     val df = Promise[SynthDef]()
     SynthDef( name, SynthGraph( thunk )).load(s,completion = action( sd => flow{ df << sd }))
     df
   }
}