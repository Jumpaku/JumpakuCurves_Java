set terminal png
set key outside


set xrange [0.0:1.0]
set yrange [0.0:1.0]
set xlabel "t"
set ylabel "Berunstein(n,i,t)"
n=10

comb(n,r) = (n==0 || r==0 || n==r) ? 1 : (n-r<r) ? comb(n,n-r) : comb(n-1, r-1)+comb(n-1,r)

bernstein(n,r,t) = comb(n,r)*((1.0-t)**(n-r))*(t**r)

plot bernstein(n,0,x) title sprintf("Bernstein(%d,%d,t)", n, 0)
replot for [i=1:n] bernstein(n,i,x) title sprintf("Bernstein(%d,%d,t)", n, i)

set output "Bernstein.png"
replot