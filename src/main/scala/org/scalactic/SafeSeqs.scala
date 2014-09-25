/*
 * Copyright 2001-2014 Artima, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.scalactic

import enablers.SafeSeqsConstraint
import scala.language.higherKinds
import scala.collection.GenSeq
import scala.collection.Seq

trait SafeSeqs {
  implicit class CovariantSeqContainifier[E, SEQ[+e] <: GenSeq[e]](leftSideGenSeq: SEQ[E]) {
    def sContains[R](rightSideEle: R)(implicit ev: SafeSeqsConstraint[SEQ[E], R]): Boolean =
      leftSideGenSeq match {
        case seq: Seq[_] => seq.contains(rightSideEle)
        case _ => leftSideGenSeq.exists(_ == rightSideEle)
      }
    def sIndexOf[R](rightSideEle: R, from: Int)(implicit ev: SafeSeqsConstraint[SEQ[E], R]): Int =
      ev.indexOf(leftSideGenSeq, rightSideEle, from)
    def sIndexOf[R](rightSideEle: R)(implicit ev: SafeSeqsConstraint[SEQ[E], R]): Int =
      ev.indexOf(leftSideGenSeq, rightSideEle, 0)
  }
  implicit class InvariantSeqContainifier[E, SEQ[e] <: GenSeq[e]](leftSideGenSeq: SEQ[E]) {
    def sContains[R](rightSideEle: R)(implicit ev: R <:< E): Boolean =
      leftSideGenSeq match {
        case seq: Seq[_] => seq.contains(rightSideEle)
        case _ => leftSideGenSeq.exists(_ == rightSideEle)
      }
    def sIndexOf[R](rightSideEle: R, from: Int)(implicit ev: R <:< E): Int =
      leftSideGenSeq.indexOf(rightSideEle, from)
    def sIndexOf[R](rightSideEle: R)(implicit ev: R <:< E): Int =
      leftSideGenSeq.indexOf(rightSideEle, 0)
  }
  implicit class ArrayContainifier[E, ARRAY[e] <: Array[e]](leftSideArray: ARRAY[E]) {
    def sContains[R](rightSideEle: R)(implicit ev: R <:< E): Boolean =
      leftSideArray.contains(rightSideEle)
    def sIndexOf[R](rightSideEle: R, from: Int)(implicit ev: R <:< E): Int =
      leftSideArray.indexOf(rightSideEle, from)
    def sIndexOf[R](rightSideEle: R)(implicit ev: R <:< E): Int =
      leftSideArray.indexOf(rightSideEle)
  }
}

object SafeSeqs extends SafeSeqs

