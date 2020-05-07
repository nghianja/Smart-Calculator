# write your code here
def is_minus(token):
    return token[0] == '-' and len(token) % 2 == 1


while True:
    numbers = input().split()
    if len(numbers) == 0:
        continue
    if len(numbers) == 1:
        if numbers[0] == "/help":
            print('The program calculates the sum of numbers. It supports both unary and binary minus operators.')
        elif numbers[0] == "/exit":
            break
        else:
            print(int(numbers[0]))
    else:
        total = int(numbers[0])
        for i in range(1, len(numbers), 2):
            if is_minus(numbers[i]):
                total -= int(numbers[i + 1])
            else:
                total += int(numbers[i + 1])
        print(total)
print('Bye!')
