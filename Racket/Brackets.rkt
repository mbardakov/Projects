#lang racket

; counts the number of times target occurs in list
(define (occurrences lst target)
    (length (filter (curry equal? target) lst)))

; accumulative recursive function for (brackets) below, storing the list of
; open/closed brackets so far in a parameter
(define (brackets\acc brackets-so-far limit)
(let* (
    [lparen (occurrences brackets-so-far 'l)]
    [rparen (occurrences brackets-so-far 'r)])
    (cond
        [(and (equal? limit lparen) (equal? limit rparen)) (list brackets-so-far)]
        [(equal? lparen rparen) (brackets\acc (append brackets-so-far (list 'l)) limit)]
        [(equal? limit lparen) (brackets\acc (append brackets-so-far (list 'r)) limit)]
        [(append (brackets\acc (append brackets-so-far (list 'l)) limit) 
               (brackets\acc (append brackets-so-far (list 'r)) limit))])))

; given a number n, returns all valid permutations of n open/closed brackets, such that
; every open bracket has a matching closed bracket afterwards
(define (brackets limit) (brackets\acc empty limit))

(brackets 1)
(brackets 2)
(brackets 3)

; assertion function for testing; doesn't return, just logs
(define (check-expect name result expect)
    (cond
        [(equal? result expect) (eprintf "test ~a passed!~n" name)]
        [(eprintf "test ~a failed, expected ~a, got ~a~n" name result expect)]))

; test suite
(define (run-tests)
    (begin
        (check-expect "empty occurrences" (occurrences '() 'a) 0)
        (check-expect "single occurrence" (occurrences '(1 2 3) 1) 1)
        (check-expect "multiple occurrences" (occurrences '(1 1 2 3) 1)))))

; (run-tests)