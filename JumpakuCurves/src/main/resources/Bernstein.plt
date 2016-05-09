n=10

set terminal png size 640,480
set key outside
set size ratio 1.0

set xrange [0.0:1.0]
set yrange [0.0:1.0]
set xlabel "t"
set ylabel "Berunstein(n,i,t)"

set samples 1000
comb(n,r) = (n==0 || r==0 || n==r) ? 1 : (n-r<r) ? comb(n,n-r) : comb(n-1, r-1)+comb(n-1,r)

bernstein(n,r,t) = comb(n,r)*((1.0-t)**(n-r))*(t**r)

plot bernstein(n,0,x) title sprintf("Bernstein(%d,%d,t)", n, 0)
replot for [i=1:n] bernstein(n,i,x) title sprintf("Bernstein(%d,%d,t)", n, i)

set output "Bernstein.png"
replot