/**
 * Created by IntelliJ IDEA.
 * User: miguelnegrao
 * Date: 11/07/12
 * Time: 13:38
 * To change this template use File | Settings | File Templates.
 */

import akka.dispatch._
import Future._
import de.sciss.synth._
import de.sciss.synth.ugen._

object RunTestNiceSyntax {

  def apply(s:Server) {

    //for some weird reason can't do df().play...
    def makeSynth(sd:SynthDef,buf:Int) = (sd.play( s, Seq(stringDoubleControlSet( ("buf", buf) ) ) ))

    val path = "sounds/a11wlk01.wav"

    val df = DataFlowSynthDef.load("play",s)({ Out.ar(0, PlayBuf.ar(1,"buf".kr)) })
    val buf = DataFlowBuffer.readChannel(s, path, channels = Seq(0))
    val synth = Promise[Synth]()

    flow {
      println("synthdef loaded:"+df())
      println("playing buf "+buf())
      synth << makeSynth(df(), buf().id.toDouble)
    }

    println("sending synthdef")
    println("start loading file "+path)

  }

}