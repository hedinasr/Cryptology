<div id="table-of-contents">
<h2>Table of Contents</h2>
<div id="text-table-of-contents">
<ul>
<li><a href="#orgheadline9">1. Cryptosystem</a>
<ul>
<li><a href="#orgheadline4">1.1. RSA</a>
<ul>
<li><a href="#orgheadline1">1.1.1. Key generation</a></li>
<li><a href="#orgheadline2">1.1.2. Encrypt</a></li>
<li><a href="#orgheadline3">1.1.3. Decrypt</a></li>
</ul>
</li>
<li><a href="#orgheadline8">1.2. ElGamal</a>
<ul>
<li><a href="#orgheadline5">1.2.1. Key generation</a></li>
<li><a href="#orgheadline6">1.2.2. Encrypt</a></li>
<li><a href="#orgheadline7">1.2.3. Decrypt</a></li>
</ul>
</li>
</ul>
</li>
</ul>
</div>
</div>


# Cryptosystem<a id="orgheadline9"></a>

Java implementation of RSA & ElGamal cryptosystems

## RSA<a id="orgheadline4"></a>

Security of RSA depends on the difficulty of factoring large integers.

### Key generation<a id="orgheadline1"></a>

1.  Choose the size key (integer)
2.  Construct two random prime integer *p* and *q* as :
    -   2<sup>[k/2]-1</sup> ≤ *p*, *q* ≤ 2<sup>[k/2]-1</sup> - 1
3.  N = p \* q
4.  Choose e in (Z/<sub>φ(N)</sub>Z)<sup>\*</sup> and compute *d* as :
    -   *ed* ≡ 1 (mod φ(*N*))

<table border="2" cellspacing="0" cellpadding="6" rules="groups" frame="hsides">


<colgroup>
<col  class="org-left" />

<col  class="org-left" />
</colgroup>
<thead>
<tr>
<th scope="col" class="org-left">public key</th>
<th scope="col" class="org-left">secret key</th>
</tr>
</thead>

<tbody>
<tr>
<td class="org-left">(*N*, *e*)</td>
<td class="org-left">(*d*, *p*, *q*)</td>
</tr>
</tbody>
</table>

### Encrypt<a id="orgheadline2"></a>

A message *m* is in Z/<sub>N</sub>Z (the size of the message is less or equal to the size key)

-   Alice has the public key (*N*, *e*)
-   *c* = m<sup>e</sup> (mod *n*)

### Decrypt<a id="orgheadline3"></a>

-   Bob has the private key (*d*, *p*, *q*)
-   c<sup>d</sup> (mod *N*) = *m*

## ElGamal<a id="orgheadline8"></a>

Security of the ElGamal algorithm depends on the difficulty of computing discrete logs
in a large prime modulus

### Key generation<a id="orgheadline5"></a>

1.  Choose a random prime integer *p* as *p* = 2 *p*' + 1 with *p*' is prime
2.  Choose a random integer in (Z/<sub>p</sub>Z)<sup>\*</sup> of order *p*'
3.  Choose a random integer *x* in [0, *p*' - 1]
4.  Compute *h* = g<sup>x</sup> in Z/<sub>p</sub>Z

<table border="2" cellspacing="0" cellpadding="6" rules="groups" frame="hsides">


<colgroup>
<col  class="org-left" />

<col  class="org-left" />
</colgroup>
<thead>
<tr>
<th scope="col" class="org-left">public key</th>
<th scope="col" class="org-left">secret key</th>
</tr>
</thead>

<tbody>
<tr>
<td class="org-left">(*p*, *g*, *h*)</td>
<td class="org-left">(*p*, *x*)</td>
</tr>
</tbody>
</table>

### Encrypt<a id="orgheadline6"></a>

1.  Choose a random *r* in [1, *p*' - 1]
2.  The encrypted message is (g<sup>r</sup>, m\*h<sup>r</sup>)

### Decrypt<a id="orgheadline7"></a>

-   Entry : (g<sup>r</sup>, m\*h<sup>r</sup>)
    -   h = g<sup>x</sup> <sup><a id="fnr.1" class="footref" href="#fn.1">1</a></sup>
    -   (g<sup>r</sup>)<sup>x</sup> = (g<sup>x</sup>)<sup>r</sup> = h<sup>r</sup>
    -   message = m\*h<sup>r</sup>/h<sup>r</sup>

<div id="footnotes">
<h2 class="footnotes">Footnotes: </h2>
<div id="text-footnotes">

<div class="footdef"><sup><a id="fn.1" class="footnum" href="#fnr.1">1</a></sup> <div class="footpara">Cf. 4. of Key generation</div></div>


</div>
</div>
