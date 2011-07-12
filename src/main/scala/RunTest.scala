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

object RunTest {

  def apply(s:Server) {

    //for some weird reason can't do df().play...
    def makeSynth(sd:SynthDef,buf:Int) = (sd.play( s, Seq(stringDoubleControlSet( ("buf", buf) ) ) ))

    val path = "sounds/a11wlk01.wav"

    val df = Promise[SynthDef]()
    val buf = Promise[Buffer]()
    val synth = Promise[Synth]()

    flow {
      println("synthdef loaded:"+df())
      println("playing buf "+buf())
      synth << makeSynth(df(), buf().id.toDouble)
    }

    println("sending synthdef")
    SynthDef("play")({ Out.ar(0, PlayBuf.ar(1,"buf".kr)) }).load(s,completion = action( sd => flow{ df << sd }))
    println("start loading file "+path)
    Buffer.readChannel(s, path, channels = Seq(0), completion = action({b => println("finished loading buffer "+b); flow{ buf << b }}))

  }

}