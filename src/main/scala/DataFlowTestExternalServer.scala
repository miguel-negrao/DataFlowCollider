/**
 *  © Miguel Negrão 2011
 *  gpl v3
 *  Date: 11/07/12
 *
This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import akka.dispatch._
import Future._
import de.sciss.synth._
import de.sciss.synth.ugen._
object DataFlowTestExternalServer {
  def main(args:Array[String]) {

    val path = "sounds/a11wlk01.wav"

    Server.connect() {
      case ServerConnection.Running( s ) =>

        val df = Promise[SynthDef]()
        val buf = Promise[Buffer]()
        val synth = Promise[Synth]()
        flow {
          println("synthdef loaded:"+df())
          println("playing buf "+buf())
          def makeSynth(sd:SynthDef,buf:Int) = (sd.play( s, Seq(stringDoubleControlSet( ("buf", buf) ) ) ))
          synth << makeSynth(df(), buf().id.toDouble)
        }
        println("sending synthdef")
        SynthDef("play")({ Out.ar(0, PlayBuf.ar(1,"buf".kr)) }).load(s,completion = action( sd => flow{ df << sd }))
        println("start loading file "+path)
        Buffer.readChannel(s, path, channels = Seq(0), completion = action({b => println("finished loading buffer "+b); flow{ buf << b }}))
    }
  }
}
