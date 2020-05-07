import re


# class for control commands within loop
class CommandError(Exception):
    def __init__(self, message):
        self.message = message


assignments = {}


def is_alpha(string):
    for char in string:
        if not char.isalpha():
            return False
    return True


def is_assignment(string):
    tokens = string.split('=')
    if len(tokens) == 2:
        key = tokens[0].strip()
        if not is_alpha(key):
            raise TypeError('Invalid identifier')
        valstr = tokens[1].strip()
        if not is_alpha(valstr):
            try:
                value = int(valstr)
            except ValueError:
                raise ValueError('Invalid assignment')
        else:
            value = assignments.get(valstr)
            if value is None:
                raise NameError('Unknown variable')
        assignments[key] = value
        return True
    elif len(tokens) > 2:
        raise ValueError('Invalid assignment')
    return False


def is_command(string):
    if string[0] == "/":
        if line == "/help":
            print('The program calculates the sum of numbers. It supports both unary and binary minus operators.')
        elif line == "/exit":
            raise CommandError('Exit command')
        else:
            raise CommandError('Unknown command')
        return True
    return False


def is_minus(token):
    return token[0] == '-' and len(token) % 2 == 1


def print_sum(string):
    numbers = re.split('\\s+', string)
    if not is_alpha(numbers[0]):
        total = int(numbers[0])
    else:
        total = assignments.get(numbers[0])
        if total is None:
            raise NameError('Unknown variable')
    for i in range(1, len(numbers), 2):
        valstr = numbers[i + 1]
        if not is_alpha(valstr):
            value = int(valstr)
        else:
            value = assignments.get(valstr)
            if value is None:
                raise NameError('Unknown variable')
        if is_minus(numbers[i]):
            total -= value
        else:
            total += value
    print(total)


while True:
    line = input()
    try:
        if len(line) == 0 or is_command(line) or is_assignment(line):
            continue
        print_sum(line)
    except CommandError as ce:
        if ce.message == "Exit command":
            break
        print(ce.message)
    except NameError as ne:
        print(ne)
    except TypeError as te:
        print(te)
    except ValueError as ve:
        print(ve)
print('Bye!')
