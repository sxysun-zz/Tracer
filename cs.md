### Compressive Sensing

#### Mechanism of Classical CS
0. 假设: 
* 信号稀疏 -> 在某个基（变换域）上线性组合系数多为0 (terminology: K-Sparse -> there are K coefficients that are not zeros)
* 非相关测量 -> (Restricted Isometry Property) of measurement matrix and basis matrix -> _phi_ cannot represent sparse signnal on _psi_
* 测量矩阵需要随机性，可以使用高斯分布，追踪时测量masked image，得到一组系数，这些随机测量矩阵和小波矩阵大概率RIP
* 到这里可以得到 y, phi, psi -> 接下来直接变成最优化问题求x使得x在小波上最稀疏
* __BEAM TRACTING__? -> __Anti Aliasing__
1. l1 范数最小化: 
* l0 minimization is NPC
* 
2. 总结:
* Scala 伪代码表示
``` scala
val y_sensed : Vector[m]
val x_original : Vector[n] // m << n

val W_basis : Matrix[n, n] // the best basis to represent x a.k.a with most zero coefficients
val Phi_coefficients : Matrix[n, n] // the coefficients of x_original in subspace W_basis
val Psi_measure : Matrix[m, n] // the matrix with linearly independent basis than W_basis

// several assumptions
// in Back-Nour Form
x_original := W_basis * Phi_coefficients
y_sensed := Psi_measure * x_original
         := Psi_measure * W_basis * Phi_coefficients

// then we can get the original signal by the following: 
Psi_measure * W_basis := Matrix[m, n]

// algorithm
// \ell 0 minimization
min(Phi_coefficients s.t. _ not zero)
// \ell 1 minimization

```
3. __Pixelwise or Beam tracing__

#### Other techniques?
0. 过冗余字典 Over-complete dictionary (more columns than rows)
* dictionary means it is a good basis for vector, on which is sparse
* Better at processing __high entropy__ images
* can get dictionary through __ML__ approach

#### Applications
0. 