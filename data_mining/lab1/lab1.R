# Exercise 1.1

colors <- c("RED", "GREEN", "BLUE", "WHITE")
colors <- factor(colors)
is.factor(colors)
pattern <- sample(colors, 500, replace = TRUE, prob = c(0.25, 0.25, 0.25, 0.25))
pattern1 <- data.frame(pattern)
pattern2 <- as.matrix(pattern)
colors1 <- as.list(colors)
expand.grid(colors,colors1)

# Exercise 1.2

data(cars)
cars2 <- cars
cars2$speed <- cars$speed / 1.61 * 1000 / 3600
cars2$dist <- cars2$dist / 1.61 * 1000
plot(speed ~ dist, cars2)
linmod <- lm(speed ~ dist, cars2)
abline(linmod$coefficients, col = "blue")
cars$speed2 <- cars2$speed

# Exercise 1.3

age <- c( 13, 15, 16, 16, 19, 20, 20, 21, 22, 22, 25, 25, 25, 25, 30, 33, 33, 35, 35, 35, 35, 36, 40, 45, 46, 52, 70)
min(age)
mode(age)
mean(age)
#midrange(age)
summary(age)
fivenum(age)
boxplot(age)

# Exercise 1.4

age <- c(23, 23, 27, 27, 39, 41, 47, 49, 50, 52, 54, 54, 56, 57, 58, 58, 60, 61)
fat <- c(9.5, 26.5, 7.8, 17.8, 31.4, 25.9, 27.4, 27.2, 31.2, 34.6, 42.5, 28.8, 33.4, 30.2, 34.1, 32.9, 41.2, 35.7)
hospital_data <- data.frame(age,fat)
summary(hospital_data$age)
summary(hospital_data$fat)
boxplot(hospital_data$age)
boxplot(hospital_data$fat)
plot(hospital_data$age ~ hospital_data$fat)
cor(hospital_data$age, hospital_data$fat)
