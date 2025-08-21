"""Combined arithmetic operations in a single module.

This file consolidates the various addition and subtraction helpers
from the previous `add_subtract_*.py` modules.
"""

def add(a, b):
    return a + b


def subtract(a, b):
    return a - b


def addition(x, y):
    return x + y


def subtraction(x, y):
    return x - y


class Calculator1:
    def add(self, n1, n2):
        return n1 + n2

    def subtract(self, n1, n2):
        return n1 - n2


def sum_numbers(numbers):
    return sum(numbers)


def subtract_numbers(numbers):
    result = numbers[0]
    for n in numbers[1:]:
        result -= n
    return result


def add_and_subtract(a, b):
    return {"add": a + b, "subtract": a - b}


def add_multiple(*args):
    return sum(args)


def subtract_multiple(*args):
    if not args:
        return 0
    result = args[0]
    for n in args[1:]:
        result -= n
    return result


class MathOps:
    @staticmethod
    def add(x, y):
        return x + y

    @staticmethod
    def subtract(x, y):
        return x - y


def add_list(lst):
    return sum(lst)


def subtract_list(lst):
    if not lst:
        return 0
    result = lst[0]
    for i in range(1, len(lst)):
        result -= lst[i]
    return result


def calculate_add_sub(a, b):
    addition = a + b
    subtraction = a - b
    return addition, subtraction
