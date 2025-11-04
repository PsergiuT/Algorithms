#include <stdio.h>
#include <stdint.h>
#include <math.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>

uint64_t g_SmallPrimes[] = {
    2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
    73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151,
    157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233,
    239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317,
    331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419,
    421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503,
    509, 521, 523, 541
};

uint64_t A_to_power_B_mod_n(uint64_t A, uint64_t B, uint64_t N) {
    //O(log(B)) complexity
    uint64_t result = 1;
    A = A % N;

    while (B > 0) {
        if (B % 2 == 1)
            result = (result * A) % N;
        A = (A * A) % N;
        B = B / 2;
    }

    return result;
}

bool smallPrimeDivisible(uint64_t number) {
    for (int i = 0; i < 100; i++) {
        if (number % g_SmallPrimes[i] == 0)
            return true;
    }

    return false;
}

bool MillerRabinPrimeCheck(uint64_t number, int NumberOfSteps) {
    if (smallPrimeDivisible(number))
        return false;

    for (int base = 2; base < NumberOfSteps; base ++) {
        bool prevIs1 = true;
        uint64_t power = number - 1;

        if (A_to_power_B_mod_n(base, power, number) != 1)
            return false;

        while (power % 2 == 0) {
            //O(log(N)^2)
            power /= 2;
            uint64_t current = A_to_power_B_mod_n(base, power, number);
            if (prevIs1)
                if (current == number - 1)
                    prevIs1 = false;
                else if (current != 1)
                    return false;
        }
    }

    // double probability = pow(0.25, NumberOfSteps);
    // probability = (1 - probability) * 100;
    // if (isPrime) {
    //     printf("The number %llu IS prime.\n", number);
    //     printf("%lf%% sure.\n", probability);
    // }

    // else
    //     printf("The number %llu is NOT prime.\n", number);
    return true;
}


bool MillerRabin(uint64_t number, double probability) {
    const double result = 1 - (probability/100);
    const double base = 0.25;
    const double numberOfStepsFloat = log10(result)/log10(base);
    const int numberOfSteps = (int) (numberOfStepsFloat + 1);

    if (MillerRabinPrimeCheck(number, numberOfSteps)) {
        double probability = pow(0.25, numberOfSteps);
        probability = (1 - probability) * 100;
        printf("The number %llu IS prime.\n", number);
        printf("%lf%% sure.\n", probability);
        return true;
    }

    return false;
}


void GenerateBigPrime(int numberDigits, double probability) {
    const uint64_t lowerBound = (uint64_t) pow(10, numberDigits - 1);
    const uint64_t highBound = (uint64_t) pow(10, numberDigits) - 1;

    for (uint64_t i = lowerBound; i <= highBound; i++) {
        if (MillerRabin(i, probability)) {
            printf("Number: %llu is prime.\n", i);
            return;
        }
    }

    // while (true) {
    //     srand(time(NULL));
    //     const uint64_t number = rand() % (highBound - lowerBound + 1) + lowerBound;
    //
    //
    //
    // }
}

int main() {
    uint64_t numberOfDigits;
    double probability;

    printf("Enter number of digits: ");
    scanf("%llu", &numberOfDigits);
    printf("Enter probability: ");
    scanf("%lf", &probability);

    GenerateBigPrime(numberOfDigits, probability);
    return 0;
}