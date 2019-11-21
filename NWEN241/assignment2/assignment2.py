def add_vectors(vector_1, vector_2):
    """Returns a list object representing the result of adding two vectors together.

       Arguments:
       vector_1 -- list representing first vector
       vector_2 -- list representing second vector

       Error checking:
       Both arguments must be of type list.
       Both vectors must of the same length.
       Only vector elements of type int can be added together.
    """
    if not isinstance(vector_1, list) and not isinstance(vector_2, list):
        print('Error: first argument is not a list\nError: second argument is not a list')
        return
    if not isinstance(vector_1, list):
        print('Error: first argument is not a list')
        return
    if not isinstance(vector_2, list):
        print('Error: second argument is not a list')
        return
    elif len(vector_1) != len(vector_2):
        print('Error: lengths of the two vectors are different')
        return
    list1 = []

    for index, elem in enumerate(vector_1):
        if isinstance(vector_1[index], int) and isinstance(vector_2[index], int):
            list1.append(vector_1[index] + vector_2[index])

        else:
            print('Error: attempted to add incompatible ' + str(vector_1[index]) + ' to ' + str(vector_2[index]))
            return

    return list1


def print_frequency(some_text):
    """Prints a table of letter frequencies within a string. 

       Non-letter characters are ignored (use .isalpha()).
       Table is sorted alphabetically.
       Letter case is ignored.
       Two blank spaces will separate the letter from its count.

       Returns None in all cases.

       Argument:
       some_text -- string containing the text to be analysed.

       Error checking:
       The argument must be a string object.
    """
    if not isinstance(some_text, str):
        print('Error: only accepts strings')
        return

    dict1 = {}

    for char in some_text:
        if char.isalpha():
            if not char.lower() in dict1:
                dict1.update({char.lower(): 1})
            else:  # is in dict1, update to have incremented value
                dict1.update({char.lower(): dict1.get(char.lower()) + 1})

    if dict1: #if dictionary is not empty
        for key,val in sorted(dict1.items()):
            print(key+'  '+str(val))

    pass


def verbing(some_text):
    """Returns a string where the each word has ing added to it if it is 3 or more characters or length and 
       ly to shorter words.

       Argument:
       some_text -- string containing the text to be analysed.

       Error checking:
       The argument must be a string object.
    """

    if not isinstance(some_text, str):
        print('Error: only accepts strings')
        return

    unverbs = some_text.split()
    verbs = []

    for string in unverbs:
        if len(string) >= 3:
            string += 'ing'
            verbs.append(string)
        else:
            string += 'ly'
            verbs.append(string)

    return ' '.join(verbs)

    pass


def add_vectors_file(file_name):
    """
    Processes a given comma seperated file and extracts the 'pay' and 'bonus' columns
    then adds the two vectors together to determine the total price.

    Argument:
    file_name -- the name of the file (assumed to exist in the same directory as the 
                 python script is executed). The file is a set of comma seperated values
                 the first line contains headers (seperated by commas) and subsequent lines
                 contain data corrosponding to each header.

    Error checking:
    The argument must be a string object
    The file must exist and be readable (no need to distinguish these cases)
    The 'pay' and 'bonus' columns must exist in the header

    """

    if not isinstance(file_name, str):
        print('Error: only accepts strings')
        return

    try:
        with open(file_name) as file_object:

            file_lines = file_object.readlines()

            if 'pay' not in file_lines[0] or 'bonus' not in file_lines[0]:
                print('Error: the file must contain pay and bonus columns')
                return

            pay_index = 0
            bonus_index = 0
            output = []

            for line in file_lines:
                s_line = line.split(',')

                if 'pay' in s_line or 'bonus' in s_line:
                    if 'bonus' in s_line:
                        pay_index = s_line.index('pay')
                        bonus_index = s_line.index('bonus')
                    elif 'bonus' not in s_line and 'bonus\n' in s_line:
                        pay_index = s_line.index('pay')
                        bonus_index = s_line.index('bonus\n')
                else:
                    output.append(int(s_line[pay_index])+int(s_line[bonus_index]))

    except FileNotFoundError:
        print('Error reading from file', file_name)
        return

    file_object.close()

    return output
