BEGIN;

-- ��������� ����� � ���������� �������� (10 �������)
INSERT INTO public.story (id, date, user_id, head, city, img, text) VALUES
(1, '2024-03-10 08:15:00', 1, '����������� ����������', '������', 'metro1.jpg', '����� �������, ����� 5. ����� ��� 50 � �������� ������� ������, ������� �� ����. ������ ���������!'),
(2, '2024-03-11 18:30:00', 2, '������ �������� � ��������', '�����-���������', 'bus42.jpg', '������� 42, ������� � ������� �����. ���� � ����������� ������.'),
(3, '2024-03-12 12:00:00', 3, '���-��������', '������������', 'scam.jpg', '� �� "�������" �������� ������ �� �������������� �����. ���������� ���������!'),
(4, '2024-02-28 20:45:00', 4, '�������� � ������� ��������', '������', 'park.jpg', '����� ����� �����, ���-�� ����� �������. ������� �������.'),
(5, '2024-03-01 07:15:00', 5, '������� � ����������', '������ ��������', 'train.jpg', '��������� ����� ����������� � ��. ������ �������� � ������.'),
(6, '2024-03-05 09:00:00', 1, '�������� � "�������� ��������"', '�����������', 'scam2.jpg', '����� ������� �������� �������� �� ������ �� ������ �����.'),
(7, '2024-03-08 22:10:00', 2, '������� � ����� ����������', '������-��-����', 'gopniki.jpg', '������� "��������", ���������� �� ���������.'),
(8, '2024-03-09 15:30:00', 3, '���� � ����� � �����', '����', 'park_night.jpg', '���� �� �������, ��� ����������� ������� ���������� ���.'),
(9, '2024-02-25 19:20:00', 4, '���������� �������', '�����������', 'witch.jpg', '� ������������ ����� ������� �� ����, ������ "�����������".'),
(10, '2024-03-07 11:45:00', 5, '������ �������� ���������', '�����������', 'minibus.jpg', '������� 145, ���� ���������, ���� �����. ������ �123�� 39 RUS.');

-- ��������� �����������-������������� (3 ����������� � ������ �������)
INSERT INTO public.comment (id, story_id, writer_id, text) VALUES
(1, 1, 2, '���� �� ��� �� ���������� �����!'),
(2, 1, 4, '������� 102 �����, �� ������'),
(3, 1, 5, '����� ��� � �������� ����� ��� ����� �����'),
(4, 2, 1, '���� ���� ���������, ������ ���� ��������'),
(5, 2, 3, '��� ������ ����� ���, ���� �� �����'),
(6, 2, 5, '�������� ������������ �������!'),
(7, 3, 1, '���������� ������������� � �������'),
(8, 3, 2, '������ 5000? �� ��� ���� ��������'),
(9, 3, 5, '��� ��� ����������� � ������� ������'),
(10, 4, 1, '����� ������ ������ ������� ������'),
(11, 4, 2, '����� ������? �������� �����������'),
(12, 4, 3, '� ���� ������ ����� �������� � ����'),
(13, 5, 1, '��� ���� ������� iPhone ���'),
(14, 5, 2, '�������� � ���� � ��������-������������'),
(15, 5, 4, '���������� ������� � �����������!'),
(16, 6, 3, '������ �����, �� ��� ��� ��������'),
(17, 6, 4, '����� ��� �� �������� �������'),
(18, 6, 5, '������� �� ����� � � �������'),
(19, 7, 1, '������ ������� ���������'),
(20, 7, 4, '�� ����� �� 5 �������, ����������'),
(21, 7, 5, '���������� �� ������ ������� �������'),
(22, 8, 2, '����� ��� ������ ������ �������'),
(23, 8, 4, '������� ��������� ����� 40 �����...'),
(24, 8, 5, '�������� ������������ �� ������'),
(25, 9, 1, '��������� � ���� ������������'),
(26, 9, 2, '��� ������ �� �������� �� �����'),
(27, 9, 3, '�������� � 2019 ����, ����� �� �������'),
(28, 10, 1, '����� ����� �������� ������ �����!'),
(29, 10, 2, '������ �������, �������� � �����'),
(30, 10, 4, '�����������-������� - ��� ������');

-- �������� �������������, ������� ������������ � ����� ����������
INSERT INTO public.users_tag (story_id, user_id) VALUES
(1, 2), (1, 3), (1, 5),
(2, 4), (2, 1),
(3, 3), (3, 5),
(4, 1), (4, 2),
(5, 3), (5, 4),
(6, 2), (6, 5),
(7, 3), (7, 4),
(8, 2);

COMMIT;
