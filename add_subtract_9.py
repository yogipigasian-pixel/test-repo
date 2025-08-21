def add_list(lst):
    return sum(lst)

def subtract_list(lst):
    if not lst:
        return 0
    result = lst[0]
    for i in range(1, len(lst)):
        result -= lst[i]
    return result