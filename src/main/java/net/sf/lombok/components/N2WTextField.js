 // Copyright 2006 Shing Hing Man
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on
// an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
// express or implied. See the License for the specific language
// governing permissions and limitations under the License.

// A javascript to convert number into word.
// Shing Hing Man 25th Auguust, 06
// Base on the script from http://4umi.com/web/javascript/numbertoword.htm

var w2n_debug=false;
var ones = new Array(
 '',
 ' one',
 ' two',
 ' three',
 ' four',
 ' five',
 ' six',
 ' seven',
 ' eight',
 ' nine',
 ' ten',
 ' eleven',
 ' twelve',
 ' thirteen',
 ' fourteen',
 ' fifteen',
 ' sixteen',
 ' seventeen',
 ' eighteen',
 ' nineteen'
);

var tens = new Array(
 '',
 '',
 ' twenty',
 ' thirty',
 ' forty',
 ' fifty',
 ' sixty',
 ' seventy',
 ' eighty',
 ' ninety'
);
var triplets = new Array(
 '',
 ' thousand',
 ' million',
 ' billion',
 ' trillion',
 ' quadrillion',
 ' quintillion',
 ' sextillion',
 ' septillion',
 ' octillion',
 ' nonillion'
);



function w2n_convertTri(num, tri, commaFlag, wrapFlag) {
 
 var str = '', comma = '',
  r = Math.round( ( num / 1000 ) - 0.5 ),
  x = Math.round( ( num / 100 ) - 0.5 ) % 10,
  y = Math.round( ( num % 100 ) - 0.5 );
 if(x > 0) { // hundreds
  str = ones[x] + ' hundred';
 }
 if(y < 20) { // ones and tens
  str += ones[y];
 } else {
  str += tens[Math.round( (y / 10) - 0.5 )] + ones[y % 10];
 }
 if(str) { // thousands
  str += triplets[tri];
 }
 if(r > 0) { // continue recursing?
  if(str) {
    comma += commaFlag ? ',' : '';
    comma += wrapFlag ? '<br />' : '';
  }
  return w2n_convertTri( r, tri + 1, commaFlag, wrapFlag ) + comma + str;
 }
 return str;
}

// Put custom validation here 
// Eg  check number x is numeric
function w2n_isValid(x){
    var valid = true;
    if (w2n_debug){
      alert(x + " is a number :" + !isNaN(x));
    }
    
    if (isNaN(x)){
       valid = false;
    } 
    else if (x.indexOf('e') > -1 || x.indexOf('E') > -1){
      // isNaN(123e33) is true !
       valid = false;
    }
    return valid;
}

// textFiledId is the html id of the input text field.
// commaFlag = true or false : to indicate if comma is placed in the word
// wrapFlag = true or false. True means the word (if long enough) will be displayed in multi-line.
// errMsg is the error message when  w2n_isValid(x) returns false.
function w2n_update(textFieldId, outputId, commaFlag, wrapFlag, errMsg) {

 var num = textFieldId.value, number, flip = 1, txt, t;
 w2n_clear(outputId);

 if( (num == null) || (num == "") ) {
    if (w2n_debug){
	alert("num=" + num);
    }  
   return; 
}

  if (w2n_isValid(num) == false){
       document.getElementById(outputId + "Error").innerHTML = errMsg;      
      if (w2n_debug) {
          alert("num=" + num + " is not numeric" );
      }
      return;
  }

 while( ( num.charAt()==' ' || num.charAt()=='0' ) && num.length>1 ) {
   if (w2n_debug) {
          alert("1 : num=" + num + "\n flip=" + flip);
   }
   num = num.substring(1);
   
 }
 if( num.charAt()=='-' ) { 
       flip *= -1; 
       num = num.substring(1); 
 }


  if (w2n_debug) {
          alert("2 : num=" + num + "\n flip=" + flip);
   }
  
  number = parseInt( num, 10 );
 
   t = number ? ( ( flip<0 ? 'minus' : '' ) + w2n_convertTri( number, 0, commaFlag, wrapFlag ) 
            + (commaFlag ? '.' : '') ) :  'zero';
  
  document.getElementById(outputId).innerHTML=t;
}

function w2n_clear(outputId){
 
   document.getElementById(outputId).innerHTML="";
   document.getElementById(outputId + "Error").innerHTML="";
  
}
