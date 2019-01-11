/*
	学号：201630665021
	姓名：廖天鸿
	最后更新时间：2018/1/5
	版本： mysql 8.0.11.0
*/
create database cprms;
use  cprms;

create table if not exists person
	(
		person_id  int AUTO_INCREMENT,
		email  varchar(50),
		title  enum("Mr","Ms","Miss","Dr","Prof")default NULL,
		name  varchar(50),
		institution  varchar(100),
		telephone_number varchar(20),
		primary key(person_id)
	);

create table if not exists pc_member
	(
		pc_code  char(4) ,
		person_id int,
		primary key(pc_code),
		foreign key(person_id) references person(person_id)
		on delete cascade
	);
create table if not exists pc_chair
	(
		pc_code  char(4),
		primary key(pc_code),
		foreign key(pc_code) references pc_member(pc_code)
		on delete cascade
	);
create table if not exists paper
	(
		paper_number int AUTO_INCREMENT, 
		title varchar(50)  , 
		paper_type enum("research","demo","industrial") default "research", 
		abstract varchar(300), 
		is_pc  enum('Y','N') default 'N', 
		decision  enum("accept","reject") default NULL ,
		primary key(paper_number)
	);
create table if not exists author
	(
		person_id int, 
		paper_number int, 
		is_contact enum('Y','N') default 'N',
		primary key(person_id,paper_number),
		foreign key(person_id) references person(person_id)
		on delete cascade,
		foreign key(paper_number) references paper(paper_number)
		on delete cascade
	);
create table if not exists referee_report
	(
		pc_code char(4), 
		paper_number int, 
		relevant  enum('Y','N','M') default NULL, 
		technically_correct enum('Y','N','M') default NULL, 
		length_and_content enum('Y','N','M') default NULL, 
		originality int, 
		impact int, 
		presentation int, 
		technical_depth int, 
		overall_rating int, 
		confidence numeric(2,1), 
		best_paper enum('Y','N') default 'N', 
		main_contribution varchar(300), 
		strong_points varchar(300), 
		weak_points	varchar(300),
		overall_summary varchar(300), 
		detailed_comments varchar(1000), 
		confidential_comments varchar(300),
		primary key(pc_code,paper_number),
		foreign key(pc_code) references pc_member(pc_code)
		on delete cascade,
		foreign key(paper_number) references paper(paper_number)
		on delete cascade
	);
create table if not exists discussion
	(
		sequence_no int AUTO_INCREMENT, 
		paper_number int, 
		pc_code char(4), 
		comments varchar(200),
		primary key(sequence_no,paper_number,pc_code),
		foreign key(pc_code) references pc_member(pc_code)
		on delete cascade,
		foreign key(paper_number) references paper(paper_number)
		on delete cascade
	);
create table if not exists prefers
	(
		pc_code char(4), 
		paper_number int, 
		preference  enum('1','2','3','4','5') default NULL,
		primary key(paper_number,pc_code),
		foreign key(pc_code) references pc_member(pc_code)
		on delete cascade,
		foreign key(paper_number) references paper(paper_number)
		on delete cascade
	);
create table if not exists assigned_to
	(
		pc_code char(4), 
		paper_number int,
		primary key(paper_number,pc_code),
		foreign key(pc_code) references pc_member(pc_code)
		on delete cascade,
		foreign key(paper_number) references paper(paper_number)
		on delete cascade
	);

	
/* COMP 3311 Task 3 Conference Paper Review Management System - Sample Database */

/*  person relation data - 18 tuples */
insert into person values (1, 'michjc@umich.edu', null, 'Michael J. Cafarella', 'University of Michigan', '+1 608 434 3702');
insert into person values (2, 'jag@umich.edu', 'Prof', 'H. V. Jagadish', 'University of Michigan', '+1 608 434 3923');
insert into person values (3, 'adityagp@cs.stanford.edu', 'Mr', 'Aditya Parameswaran', 'Stanford University', null);
insert into person values (4, 'ndalvi@yahooinc.com', 'Dr', 'Nilesh Dalvi', 'Yahoo! Research', '+1 415 3029');
insert into person values (5, 'hector@cs.stanford.edu', 'Prof', 'Hector Garcia-Molina', 'Stanford University', '+1 650 723 0685');
insert into person values (6, 'rrastogi@yahooinc.com', 'Dr', 'Rajeev Rastogi', 'Yahoo! Research', '+1 415 3045');
insert into person values (7, 'ecrestan@yahoo-inc.com', 'Dr', 'Eric Crestan', 'Yahoo! Labs', '+1 415 678 6654');
insert into person values (8, 'ppantel@yahoo-inc.com', 'Dr', 'Patrick Pantel', 'Yahoo! Labs', '+1 415 678 2345');
insert into person values (9, 'vladimir@sis.pitt.edu', 'Prof', 'Vladimir Zadorozhny', 'University of Pittsburgh', '+1 657 334 2102');
insert into person values (10, 'yfhsu@sis.pitt.edu', 'Mr', 'Ying-Feng Hsu', 'University of Pittsburgh', '+1 657 334 2105');
insert into person values (11, 'tweninge@nd.edu', 'Prof', 'Tim Weninger', 'University of Notre Dame', '+1 574 631 6770');
insert into person values (12, 'bhsu@cis.ksu.edu', 'Prof', 'William H. Hsu', 'Kansas State University', '+1 785 532 7905');
insert into person values (13, 'hanj@cs.uiuc.edu', 'Prof', 'Jiawei Han', 'University of Illinois at Urbana-Champaign', '+1 217 333-6903');
insert into person values (14, 'rajase@us.ibm.com', 'Mr', 'Rajasekar Krishnamurthy', 'IBM Research - Almaden', '+1 415 909 1190');
insert into person values (15, 'naughton@cs.wisc.edu', 'Mr', 'Jeffrey Naughton', 'University of Wisconsin', '+1 608 262 8737');
insert into person values (16, 'fred@cse.ust.hk', 'Prof', 'Fred Lochovsky', 'Hong Kong University of Science and Technology', '+852 2358 6996');
insert into person values (17, 'leichen@cse.ust.hk', 'Prof', 'Lei Chen', 'Hong Kong University of Science and Technology', '+852 2358 6980');
insert into person values (18, 'rundenst@cs.wpi.edu', 'Prof', 'Elke Rundensteiner', 'Worcester Polytechnic Institute', '+1 508 831 5815');
 
/* pc_member relation data - 7 tuples */
insert into pc_member values ('hj01', 2);
insert into pc_member values ('ec01', 7);
insert into pc_member values ('pp01', 8);
insert into pc_member values ('jh01', 13);
insert into pc_member values ('fl01', 16);
insert into pc_member values ('lc01', 17);
insert into pc_member values ('er01', 18);

/* pc_chair relation data - 1 tuple */
insert into pc_chair values ('fl01');

/* paper relation data - 6 tuples */
insert into paper values (1, 'Example-Driven Schema Mapping', 'research', 'End-users increasingly find the need to perform light-weight, customized data integration. State-of-the-art tools usually require an in-depth understanding of the semantics of multiple schemas. We propose a system, MWeaver, that facilitates data integration for end-users.', 'Y', null);

insert into paper values (2, 'Optimal Schemes for Robust Web Extraction', 'research', 'We consider the problem of constructing robust wrappers for web information extraction. We consider two models to study robustness formally: the adversarial model and probabilistic model. We demonstrate that our algorithms can reduce wrapper breakage by up to 500% over existing techniques.', 'N', null);

insert into paper values (3, 'Web-Scale Knowledge Extraction', 'research', 'We propose a classification algorithm and a rich feature set for automatically recognizing layout tables and attribute/value tables. In 79% of our Web tables, our method finds the correct protagonist in its top three returned candidates.', 'Y', null);

insert into paper values (4, 'Efficient Fusion of Historical Data', 'research', 'Historical data may include severe data conflicts that prevent researchers from obtaining the correct answers to queries on an integrated historical database. We consider an efficient approach to large-scale historical data fusion.', 'N', null);

insert into paper values (5, 'CETR - Content Extraction via Tag Ratios', 'research', 'Content Extraction via Tag Ratios (CETR) is a method to extract content text from diverse webpages using the HTML document’s tag ratios. We evaluate our approach against a large set of alternative methods, which shows that CETR achieves better content extraction performance than existing methods.', 'Y', null);

insert into paper values (6, 'Towards User-Friendly Entity Resolution', 'research', 'We explore the possibility of treating user input as an integral part of the entity resolution process. We design a simple two-stage approach that separates merging and splitting records into two separate stages.', 'N', null);

/* author relation data - 15 tuples */
insert into author values (1, 1, 'Y');
insert into author values (2, 1, 'N');
insert into author values (3, 2, 'N');
insert into author values (4, 2, 'N');
insert into author values (5, 2, 'Y');
insert into author values (6, 2, 'N');
insert into author values (7, 3, 'Y');
insert into author values (8, 3, 'N');
insert into author values (9, 4, 'Y');
insert into author values (10, 4, 'N');
insert into author values (11, 5, 'N');
insert into author values (12, 5, 'N');
insert into author values (13, 5, 'Y');
insert into author values (14, 6, 'Y');
insert into author values (15, 6, 'N');
 
/* prefers relation data - 13 tuples */
insert into prefers values ('hj01', 1, 2);
insert into prefers values ('hj01', 3, 3);
insert into prefers values ('hj01', 5, 4);
insert into prefers values ('ec01', 1, 3);
insert into prefers values ('ec01', 2, 4);
insert into prefers values ('pp01', 4, 1);
insert into prefers values ('pp01', 5, 4);
insert into prefers values ('pp01', 6, 4);
insert into prefers values ('lc01', 1, 1);
insert into prefers values ('lc01', 2, 2);
insert into prefers values ('lc01', 3, 1);
insert into prefers values ('lc01', 4, 2);
insert into prefers values ('lc01', 6, 3);

/* assigned_to relation data - 9 tuples */
insert into assigned_to values ('hj01', 3);
insert into assigned_to values ('hj01', 5);
insert into assigned_to values ('ec01', 1);
insert into assigned_to values ('ec01', 2);
insert into assigned_to values ('ec01', 4);
insert into assigned_to values ('pp01', 5);
insert into assigned_to values ('pp01', 6);
insert into assigned_to values ('jh01', 1);
insert into assigned_to values ('lc01', 4);
 
/* referee_report relation data - 7 tuples */
insert into referee_report values ('ec01', 1, 'Y', 'Y', 'Y', 8, 7, 8, 7, 8, 1.0, 'Y', 'The paper proposes a new way to do schema mappings that involve the user providing example instances of the result data. The system then constructs the schema mapping "behind the scenes" from the provided examples.', 'The described example-based schema mapping is novel in that it has not been used before specifically for the schema mapping problem.', 'The user study is fairly small-scale. It is not clear that any statistical significance can be drawn from such a small-scale study, though the results do look promising for the given example.', 'An excellent paper.', null, null);

insert into referee_report values ('jh01', 1, 'Y', 'Y', 'Y', 7, 6, 6, 7, 7, 0.9, 'N', 'The paper proposes a new way to do schema mappings.', 'The technique is novel and technique is efficient. ', 'Applicable only to small-scale schema mappings.', 'An interesting technique to do schema mappings.', null, null);

insert into referee_report values ('er01', 1, 'Y', 'N', 'Y', 6, 4, 7, 5, 3, 1.0, 'N', 'The paper proposes a new way to do schema mappings, but the algorithm is incorrect.', 'None really.', 'The algorithm is incorrect.', 'Should be rejected as it is technically not correct.', null, null);

insert into referee_report values ('ec01', 4, 'Y', 'Y', 'Y', 6, 5, 5, 6, 6, 0.9, 'N', 'When integrating data from several documents, several types of conflicts may arise in the data. These conflicts may result in inaccurate query results due to over- or under-estimation. This paper discusses three types of conflicts.', 'The paper clearly defines each of the three types of conflicts with examples and how they may affect the results of queries.', 'No convincing validation of the method on real historical data is provided.', 'The paper is suitable for the conference.', null, null);

insert into referee_report values ('lc01', 4, 'Y', 'Y', 'Y', 7, 6, 7, 6, 6, 1.0, 'N', 'This paper discusses three types of conflicts that may arise when integrating data from several documents.', 'A method for dealing with this problem for temporal conflicts is proposed.', 'There are some technical errors in the paper (see the detailed comments).', 'The paper is OK for the conference.', 'In Section 4.1 the equation for RO(t1,t2) seems to be wrong. Since you are taking the absolute value of the sum and overlap, when there is a (non-zero) time gap between two time intervals, the value for RO will still be greater than 0.', null);

insert into referee_report values ('ec01', 2, 'Y', 'Y', 'Y', 5, 4, 4, 5, 4, 0.8, 'N', 'This paper focuses on robust wrapper construction. In particular, two models are studied: the adversarial model and the probabilistic model.', 'A new approach, which has been proved to be the optimal for wrapper robustness, is proposed.', 'The paper is presented in a way which is very difficult to understand. I would like to see some examples to help explain the models.', 'This paper requires further work to make it acceptable for inclusion in the conference.', null, null);

insert into referee_report values ('jh01', 2, 'Y', 'Y', 'Y', 5, 5, 6, 6, 7, 0.8, 'N', 'A provable most robust wrapper is proposed for the two models. Experiments show the robustness of the wrapper. The paper is presented clearly and each part is well motivated.', 'Two models, adversarial model and probabilistic model, are proposed. For each model, an extraction confidence is provided.', 'The two models could be better motivated.', 'A well written paper on an interesting and timely topic that is very relevant to the conference.', null, null);

/* discussion */
insert into discussion values(1,1,'ec01','It is a good paper');
insert into discussion values(2,1,'er01','I also think so');