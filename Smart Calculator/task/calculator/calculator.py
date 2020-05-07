# write your code here
while True:
    numbers = input().split()
    if len(numbers) == 0:
        continue
    if len(numbers) == 1:
        if numbers[0] == "/exit":
            break
        print(int(numbers[0]))
    else:
        print(int(numbers[0]) + int(numbers[1]))
print('Bye!')
