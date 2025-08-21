def sum_numbers(numbers):
    return sum(numbers)

def subtract_numbers(numbers):
    result = numbers[0]
    for n in numbers[1:]:
        result -= n
    return result
