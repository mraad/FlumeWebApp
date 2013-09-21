{
  N=split($8,T,/\//)
  if(N==6){
    Y=T[3]
    M=T[4]
    D=T[5]
    H=T[6]
    print "alter table log4j add if not exists partition (year="Y",month="M",day="D",hour="H") location '/flume/"Y"/"M"/"D"/"H"';"
  }
}