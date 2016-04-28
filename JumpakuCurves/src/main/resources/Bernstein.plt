set xrange [0.0:1.0]
set yrange [0.0:1.0]

n=15

comb(n,r) = (n==0 || r==0 || n==r) ? 1 : (n-r<r) ? comb(n,n-r) : comb(n-1, r-1)+comb(n-1,r)

bernstein(n,r,t) = comb(n,r)*((1.0-t)**(n-r))*(t**r)

plot bernstein(n,0,x)
replot for [i=1:n] bernstein(n,i,x)

