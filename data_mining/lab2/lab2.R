florist <- read.table("florist.txt", header = T, sep = ",")
roses <- subset(florist, bouquet == "roses")
roses_by_day <- aggregate(roses$price, by = list(roses$date), FUN = length)
names(roses_by_day) <- c("date", "number_of_bouquets")
plot(roses_by_day)

roses_by_week <- aggregate(roses$price, by = list(roses$year, roses$week), FUN = length)
names(roses_by_week) <- c("year","week","total")
roses_by_week <- roses_by_week[order(roses_by_week$year),]

plot( roses_by_week$total ~ roses_by_week$week, type="l")


binning <- function(input_data, bin_size, FUN = mean) {
  len <- length(input_data)
  y <- vector()
  for (i in 1:len) {
    k = i %% bin_size
    if(k==0){
      k0 <- i - bin_size + 1
    }
    else{
      k0 <- i - k+1
    }
    k1 <- k0 + bin_size - 1
    if(k1>len){
      k1<-len
    }
    y[i] <- FUN(input_data[k0:k1])
  }
  return(y)
}

a<-binning(roses_by_week$total, 5)
plot( a  ~ roses_by_week$week, type="l")
