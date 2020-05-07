# write your code here
while True:
    numbers = input().split()
    if len(numbers) == 0:
        continue
    if len(numbers) == 1:
        if numbers[0] == "/help":
            print('The program calculates the sum of numbers')
        elif numbers[0] == "/exit":
            break
        else:
            print(int(numbers[0]))
    else:
        total = 0
        for n in numbers:
            total += int(n)
        print(total)
print('Bye!')
