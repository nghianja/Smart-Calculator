# write your code here
def is_minus(token):
    return token[0] == '-' and len(token) % 2 == 1


def print_sum(numbers):
    total = int(numbers[0])
    for i in range(1, len(numbers), 2):
        if is_minus(numbers[i]):
            total -= int(numbers[i + 1])
        else:
            total += int(numbers[i + 1])
    print(total)


while True:
    line = input()
    if len(line) == 0:
        continue
    if line[0] == '/':
        if line == "/help":
            print('The program calculates the sum of numbers. It supports both unary and binary minus operators.')
        elif line == "/exit":
            break
        else:
            print('Unknown command')
    else:
        try:
            print_sum(line.split())
        except ValueError:
            print('Invalid expression')
        except IndexError:
            print('Invalid expression')
print('Bye!')
