def add_multiple(*args):
    return sum(args)

def subtract_multiple(*args):
    if not args:
        return 0
    result = args[0]
    for n in args[1:]:
        result -= n
    return result
